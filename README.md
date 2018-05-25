# ELEC3542-Individual-Project, Teoh Jian Ning

## Contents

ELEC3542_Project - Java project file
Sensor Node 1 - Everything is placed in the '/home/pi/ELEC3542_Project_Files' directory. 
Sensor Node 2 - Everything is placed in the '/home/pi/ELEC3542_Project_Files' directory.
Sensor Node 3 - Everything is placed in the '/home/pi/ELEC3542_Project_Files' directory.
Server - Everything is placed in the '/home/pi/ELEC3542_Project_Files' directory.

ELEC3542_Project.jar - Runnable jar file. All devices run the same jar file, but different classes.
ELEc3542_Project.rar - archived version of 'ELEC3542_Project' java project file.

For sensor nodes, all files are placed in /home/pi/ELEC3542_Project_Files (except rc.local, which is placed at /etc/rc.local)



### Sensor Nodes

#### Sensor Node 1

![camera program structure](https://user-images.githubusercontent.com/18203755/40534794-c1c3c34c-6039-11e8-9735-b8daa6cec4c8.png)  

Command: java Sensor_Core_CameraType 1
Argument = node number

#### Sensor Node 2
![sensehat program structure](https://user-images.githubusercontent.com/18203755/40534771-b4c4a12a-6039-11e8-9813-849ef5390a04.png)  

Command: java Sensor_Core_SenseHatType 2
Argument = node number

#### Sensor Node 3
![sensehat program structure](https://user-images.githubusercontent.com/18203755/40534771-b4c4a12a-6039-11e8-9813-849ef5390a04.png)  

Command: java Sensor_Core_SenseHatType 3
Argument = node number

### Server
![camera program structure](https://user-images.githubusercontent.com/18203755/40534736-9f7df80c-6039-11e8-830e-b65d4a41f365.png)  

Command: java Server_Core 3
Argument = number of nodes



Additional Implementations:
sudo apt-get install apache2 - Used to host webpage ( /var/www/html/index.html ).
sudo apt-get install weavedconnectd - Allows remote access using remot3.it (Replicate webpage access).

### Libraries/ Plugins/ Reference


### Future Improvements
 - Proper exception handling
 - Change network infrastructure from using MQTT to a connection based one (TCP);
 - Implement RSA signature verification (integrity)
 - Restructure class files (proper threading, syncrhonization, extending etc)
