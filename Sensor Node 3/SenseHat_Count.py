from sense_hat import SenseHat

sense = SenseHat()

OFFSET_LEFT = 1
OFFSET_TOP = 2

NUMS =[1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,  # 0
       0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,  # 1
       1,1,1,0,0,1,0,1,0,1,0,0,1,1,1,  # 2
       1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,  # 3
       1,0,0,1,0,1,1,1,1,0,0,1,0,0,1,  # 4
       1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,  # 5
       1,1,1,1,0,0,1,1,1,1,0,1,1,1,1,  # 6
       1,1,1,0,0,1,0,1,0,1,0,0,1,0,0,  # 7
       1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,  # 8
       1,1,1,1,0,1,1,1,1,0,0,1,0,0,1]  # 9

# Displays a single digit (0-9)
def show_digit(val, xd, yd, r, g, b):
  offset = val * 15
  for p in range(offset, offset + 15):
    xt = p % 3
    yt = (p-offset) // 3
    sense.set_pixel(xt+xd, yt+yd, r*NUMS[p], g*NUMS[p], b*NUMS[p])

# Displays a two-digits positive number (0-99)
def show_number(val, r, g, b):
  abs_val = abs(val)
  tens = abs_val // 10
  units = abs_val % 10
  show_digit(tens, OFFSET_LEFT, OFFSET_TOP, r, g, b)
  show_digit(units, OFFSET_LEFT+4, OFFSET_TOP, r, g, b)

sum = 0
sense.clear()
show_number(sum, 100, 100, 100)
f = open("/home/pi/ELEC3542_Project_Files/Customer_Count.txt",'w')
f.write("%d" % sum)
f.close()
while(True):
	for event in sense.stick.get_events():
	    if event.action == 'pressed':
		    if event.action == 'pressed' and event.direction == 'up':
		    	print('up')
		    	sum += 1
		    	
		    if event.action == 'pressed' and event.direction == 'down':
		    	print('down')
		    	sum -= 1
		    	
		    if sum >= 0 :
                        sense.clear()
                        show_number(sum, 100, 100, 100)
                        
		    f = open("/home/pi/ELEC3542_Project_Files/Customer_Count.txt",'w')
		    f.write("%d" % sum)
		    f.close()
		    
		    
# number display: https://yaab-arduino.blogspot.hk/2016/08/display-two-digits-numbers-on-raspberry.html