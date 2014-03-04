
#include <Ultrasonic.h>
#include <IRremote.h>
#include <SoftwareSerial.h>
#include <Servo.h>
#include <LiquidCrystal.h>
//#include <Time.h>

#define NOTE_B0  31
#define NOTE_C1  33
#define NOTE_CS1 35
#define NOTE_D1  37
#define NOTE_DS1 39
#define NOTE_E1  41
#define NOTE_F1  44
#define NOTE_FS1 46
#define NOTE_G1  49
#define NOTE_GS1 52
#define NOTE_A1  55
#define NOTE_AS1 58
#define NOTE_B1  62
#define NOTE_C2  65
#define NOTE_CS2 69
#define NOTE_D2  73
#define NOTE_DS2 78
#define NOTE_E2  82
#define NOTE_F2  87
#define NOTE_FS2 93
#define NOTE_G2  98
#define NOTE_GS2 104
#define NOTE_A2  110
#define NOTE_AS2 117
#define NOTE_B2  123
#define NOTE_C3  131
#define NOTE_CS3 139
#define NOTE_D3  147
#define NOTE_DS3 156
#define NOTE_E3  165
#define NOTE_F3  175
#define NOTE_FS3 185
#define NOTE_G3  196
#define NOTE_GS3 208
#define NOTE_A3  220
#define NOTE_AS3 233
#define NOTE_B3  247
#define NOTE_C4  262
#define NOTE_CS4 277
#define NOTE_D4  294
#define NOTE_DS4 311
#define NOTE_E4  330
#define NOTE_F4  349
#define NOTE_FS4 370
#define NOTE_G4  392
#define NOTE_GS4 415
#define NOTE_A4  440
#define NOTE_AS4 466
#define NOTE_B4  494
#define NOTE_C5  523
#define NOTE_CS5 554
#define NOTE_D5  587
#define NOTE_DS5 622
#define NOTE_E5  659
#define NOTE_F5  698
#define NOTE_FS5 740
#define NOTE_G5  784
#define NOTE_GS5 831
#define NOTE_A5  880
#define NOTE_AS5 932
#define NOTE_B5  988
#define NOTE_C6  1047
#define NOTE_CS6 1109
#define NOTE_D6  1175
#define NOTE_DS6 1245
#define NOTE_E6  1319
#define NOTE_F6  1397
#define NOTE_FS6 1480
#define NOTE_G6  1568
#define NOTE_GS6 1661
#define NOTE_A6  1760
#define NOTE_AS6 1865
#define NOTE_B6  1976
#define NOTE_C7  2093
#define NOTE_CS7 2217
#define NOTE_D7  2349
#define NOTE_DS7 2489
#define NOTE_E7  2637
#define NOTE_F7  2794
#define NOTE_FS7 2960
#define NOTE_G7  3136
#define NOTE_GS7 3322
#define NOTE_A7  3520
#define NOTE_AS7 3729
#define NOTE_B7  3951
#define NOTE_C8  4186
#define NOTE_CS8 4435
#define NOTE_D8  4699
#define NOTE_DS8 4978

int melody[] = {NOTE_C4, NOTE_E4,NOTE_G4, NOTE_D5, NOTE_FS5,NOTE_A5, NOTE_CS6};

// note durations: 4 = quarter note, 8 = eighth note, etc.:
int noteDurations[] = {
  4, 4,2,4,4,2, 2 };

LiquidCrystal lcd(12, 11, 10, 13, 9, 8);
Ultrasonic ultrasonic(12,13);
int RECV_PIN = 4;
IRrecv irrecv(RECV_PIN);
decode_results results;

SoftwareSerial mySerial(0, 1);
Servo steer;
Servo motor;
String msg_one;
String msg_two;
int data;
int pot_range = 10;
int speed = 98;
int default_right = 170;
int default_left = 30;
int turn_offset = 20;
int default_speed = 98;
int default_turn = 125;
int turn = 125;
int current_turn = 125;
int temp = 118;
int offset = 1;
boolean blocked = false;
boolean enable_range = false;
boolean remote_mode = true;


void setup(void){
   //for (int thisNote = 0; thisNote < 6; thisNote++) {
    // to calculate the note duration, take one second 
    // divided by the note type.
    //e.g. quarter note = 1000 / 4, eighth note = 1000/8, etc.
    //int noteDuration = 1000/noteDurations[thisNote];
    //tone(10, melody[thisNote],noteDuration);
    // to distinguish the notes, set a minimum time between them.
    // the note's duration + 30% seems to work well:
    //int pauseBetweenNotes = noteDuration * 1.30;
    //delay(pauseBetweenNotes);
    // stop the tone playing:
    //noTone(10);
  //}
  //2 color?
  lcd.begin(16,2);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);  
  steer.attach(2);
  steer.write(default_turn); 
  motor.write(default_speed);
  motor.attach(5);
  irrecv.enableIRIn();
  mySerial.begin(9600);
  Serial.begin(9600);
  msg_one = "Ready.";
  msg_two = "";
}
void loop(void){
  lcd.setCursor(0,0);
  lcd.print(msg_one);
  lcd.setCursor(0,2);
  lcd.print(msg_two);
  //ONONONONONONONONON: remote control mode on
  //if(remote_mode) {
    //digitalWrite(8, HIGH);
    //digitalWrite( 9, LOW);
  //}
  //else {
     //digitalWrite(8, LOW);
     //digitalWrite(9, HIGH);
  //}
  if(irrecv.decode(&results)) {
    Serial.println(results.value, HEX);
    //right arrow
    if(results.value == 0xE144639C){
      // turn right
      current_turn = default_turn + turn_offset;
      steer.write(current_turn);
    }
    //left arrow
    else if(results.value == 0xE144E31C){
      // turn left
      current_turn = default_turn - turn_offset;
      steer.write(current_turn);
    }
    //number 1 key
    else if(results.value == 0xE144FB04){
      // turn offset low
      turn_offset = 10;
      offset = 1;
      msg_two = "offset = 1";
      lcd.clear();
    }
    //number 2 key
    else if(results.value == 0xE1448778){
      // turn offset medium
      turn_offset = 20;
      offset = 2;
      msg_two = "offset = 2";
      lcd.clear();
    }
    //number 3 key
    else if(results.value == 0xE14447B8){
      // turn offset high
      turn_offset = 30;
      offset = 3;
      msg_two = "offset = 3";
      lcd.clear();
    }
    //up arrow key
    else if(results.value == 0xE1446F90) {
      // increment speed
      speed += offset;
      if(speed < 0){
        speed = 0;
      }
      if(speed > 180){
        speed = 180;
      }
      msg_one = "Forward";
      lcd.clear();
    }
    //down arrow key
    else if(results.value == 0xE144EF10){
      // decrement speed
      speed -= offset;
      if(speed < 0){
        speed = 0;
      }
      if(speed > 180){
        speed = 180;
      }
      msg_one = "Reverse";
      lcd.clear();
    }
    //stop button
    else if(results.value == 0xE14406F9){
      // stopped
      speed = default_speed;
      msg_one = "Stopped";
      lcd.clear();
    }
    //enter key in arrow key pad
    else if(results.value == 0xE1444EB1){
      if(current_turn < default_turn) {
          steer.write(default_turn + 10);
      }
      else if(current_turn > default_turn) {
          steer.write(default_turn - 10);
      }
      delay(150);
      steer.write(default_turn);
      msg_one = "Turn Reset";
      lcd.clear();
    }
    //mute key
    else if(results.value == 0xE144CF30){
      if(enable_range){
        enable_range = false;
      }
      else {
        enable_range = true;
      }
      blocked = false;
      temp = default_speed;
      speed = default_speed;
    }
    //mode button
    else if(results.value == 0xE1443AC5){
      //switch control between pot and remote
      if(!remote_mode){
        remote_mode = true;
      }
      else {
        remote_mode = false;
      }
    }
    irrecv.resume(); // Receive the next value  
  }
  //read the potentiometer and scale value
  data = analogRead(A0)/5.9;
  //pot_range = data/8 ;
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  // MOTOR WRITE OMGWTFBBQ!!!
  if(remote_mode){
    motor.write(speed);
  }
  else {
    //motor.writeMicroseconds(microseconds);
    motor.write(data);
  }
  //PWM
  //digitalWrite(5, HIGH);
  //delayMicroseconds(data);
  //digitalWrite(5, LOW);
  //delay(20);
  //take range measurement
  //int range = ultrasonic.Ranging(CM);
  if(remote_mode){
    //Serial.println(speed);
  }
  else {
    Serial.println(data);
  }
  //distance sensor enabled
  //if(enable_range){
    // if(range < pot_range && !blocked){
        //play sound or music? detect object?
      //  blocked = true;
        //temp = speed;
        //speed = default_speed;
        //delay(200);
     //}
     //else if (range > pot_range && blocked == true){
       //delay(200);
        //speed = temp; 
        //blocked = false;
     ///}
  //}
  if (mySerial.available() && mySerial.isListening()) {
    if(!mySerial.overflow()){
    char vals[25];
    char inChar;
    int index = 0;
    char val = mySerial.read();
    if(val != 0){
     Serial.println(val);
    } 
    
    switch(val){ // Perform an action depending on the command
    case '1'://Move Forward
    turn_offset = 10;
    break;
    case '2'://Move Forward
    turn_offset = 20;
    break;
     case '3'://Move Forward
    turn_offset = 40;
    break;
  case 'w'://Move Forward
    speed += offset;
      if(speed < 0){
        speed = 0;
      }
      if(speed > 180){
        speed = 180;
      }
  break;

  case 's'://Move Backwards
   speed -= offset;
      if(speed < 0){
        speed = 0;
      }
      if(speed > 180){
        speed = 180;
      }
  break;

  case 'a'://Turn Left
  steer.write(default_turn-turn_offset);
  //delay(100);
  break;

  case 'd'://Turn Right
  steer.write(default_turn+turn_offset);
  //delay(100);
  break;
  
  case 'x'://turbo
  speed = default_speed;
  break;
  
  case 'r'://turbo
  steer.write(100);
  //delay(100);
  break;
  
  case '-': //command
  delay(10);
    while (mySerial.available() > 0){
        if(index < 25) // One less than the size of the array
        {
            inChar = mySerial.read(); // Read a character
            Serial.print(inChar);
            delay(10);
            vals[index] = inChar; // Store it
            index++; // Increment where to write next
            //vals[index] = '\0'; // Null terminate the string
       }
    }
   //get message to print
   msg_one = "Command:";
   msg_two = vals;
   msg_two = msg_two.substring(0, index-1);
   msg_two.trim();
   index = 0;
   lcd.clear();
   Serial.println();
  break;

  default:
  break;
  }
  }
  else {
    mySerial.flush();
    Serial.println("overflow");
    delay(1000);
  }
  }
}



