![](http://fs5.directupload.net/images/171026/xo2v64gw.png)
# Welcome to Chatbot4Customer.
Chatbot4Customer connects the SAP Cloud for Customer with the IBM Watson Chatbot.

## Information.

Why should you always have to click around in C4C when you can quickly create a new appointment with the Chatbot? Chatbot4Customer helps you to perform C4C Functions, without clicking around in the C4C and without spending time to wait for the loadings.

* Currently only available in German
* Using IBM Watson
* Using SAP Cloud for Customer SOAP Webservices

## Video.

 [Description Video](https://drive.google.com/file/d/0B95aqtjL8h0XZmktcE04R2xobjQ/view)

## Important.

The entire code was written in just 4 days, so I know there are a lot of places that need to be improved. The code is far away from to be perfect, but the main point of this project was to work with the IBM Chatbot and to build a backend that communicates with C4C web services via SOAP. 

## How to use.

First of all, the file 'application.propeties' needs to be customized.

##### user      =  your C4C User  (for example: peterm)
##### password  = password of your User   (for example: 12345)
##### c4c        = your C4C tenant         (my231523.crm.ondemand.com) 
##### internalid = internalid of your user (80023138)                   

Also you can customize to your IBM Chatbot Workspace if you want to.

## Commands

***

### Get Customer by city
##### Description
Returns all customers located in this city.
##### Required 
City: München, Augsburg, Ingolstadt, Berlin, Köln, Hamburg
##### Example
Gebe mir alle Kunden in München an.

***

### Appointments today
##### Description
Returns all appointments that are in the calendar today
##### Example
Welche Termine habe ich heute? 

***

### Appointment next
##### Description
Returns the next scheduled appointment 
##### Example
Was steht als nächstes an?

***

### Appointment time left
##### Description
Returns Returns the time left until the next appointment
##### Example
Wie viel Zeit habe ich bis zum nächsten Termin?

***

### Appointment create
##### Description
Creates an Appointment for today
##### Required 
Time (for example: 19:00) & "Betreff: " (Name of Appointment, must be at the end of your command!)
##### Example
Erstelle einen Termin um 5 Uhr Abends mit dem Betreff: Termin

***
## 