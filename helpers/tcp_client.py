import socket
import time

LOCALHOST = "127.0.0.1"
OTHER_HOST = "192.168.0.102"
PORT = 3007


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((OTHER_HOST, PORT))
cnt_msg = 0
while True:
	if(cnt_msg > 9):
		s.send(b'b')
		break
	s.send(b'X')
	data = s.recv(100).decode()
	print(data)
	time.sleep(1)
	cnt_msg += 1
s.close()
