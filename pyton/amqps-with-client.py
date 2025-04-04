from proton import Message
from proton.reactor import Container
from proton.handlers import MessagingHandler
from utils import init_ssl_auth

class Client(MessagingHandler):
    def __init__(self, server, queue):
        super(Client, self).__init__()
        self.server = server
        self.queue = queue

    def on_start(self, event):
        conn = event.container.connect(self.server)
        event.container.create_receiver(conn, self.queue)
        event.container.create_sender(conn, self.queue)

    def on_sendable(self, event):
        event.sender.send(Message(body="Hello World!"))
        event.sender.close()

    def on_message(self, event):
        print(event.message.body)
        event.receiver.close()
        event.connection.close()

#URL amqps acceptor with certificate auth
url = 'amqps://localhost:5671'
queue = 'test'
container = Container(Client(url, queue))
init_ssl_auth(container)
container.run()
