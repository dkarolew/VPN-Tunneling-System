import socket


host = '127.0.0.1'       
port = 6000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((host, port))

s.listen(5)
conn, addr = s.accept()
print('Connected by', addr)
while True:

    try:
        data = conn.recv(100)

        if not data: break

        print(data.decode('utf-8'))
        conn.send(b'y')

    except socket.error:
        print ("Error Occured.")
        break

conn.close()