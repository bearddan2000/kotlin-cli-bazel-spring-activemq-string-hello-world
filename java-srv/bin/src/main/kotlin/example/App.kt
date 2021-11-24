package example

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.Session
import javax.jms.Message;
import org.apache.activemq.ActiveMQConnectionFactory

@SpringBootApplication
open class App
{

  val MQAddress = "tcp://mq-srv:61616"

  val QueueName = "SampleQueue"

  fun receive() {
    val connFactory = ActiveMQConnectionFactory(MQAddress)

    val conn = connFactory.createConnection()!!

    val sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)!!

    val dest = sess.createQueue(QueueName)

    val cons = sess.createConsumer(dest)!!

    conn.start()

    val msg = cons.receive()

    println(msg)

    conn.close()
  }

  fun send() {
    val connFactory = ActiveMQConnectionFactory(MQAddress)

    val conn = connFactory.createConnection()!!

    val sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)!!

    val dest = sess.createQueue(QueueName)

    val cons = sess.createProducer(dest)!!

    conn.start()

    val msg :Message = sess.createTextMessage("hello world")

  	cons.send(msg)

    conn.close()
  }

}
fun main(args: Array<String>) {
 runApplication<App>(*args)

val app :App = App()

 app.send()

 app.receive()
}
