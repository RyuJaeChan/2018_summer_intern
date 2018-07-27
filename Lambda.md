#Java SE 8 : Lambda Quick Start


Overview
=========
## Purpose

이 튜토리얼은 Java SE 8에 포함되는 새로운 람다 표현식을 소개합니다

## Time to Complete

약 1시간

## Introduction

람다 표현식은 Java SE8에 포함된 새롭고 중요한 특징입니다. 이것들은 하나의 메소드 인터페이스를 표현식을 사용하여 나타내는 명백하고 간결한 방법을 선사합니다. 람다 표현식은 또한 ```Collection``` 라이브러리의 사용을 더 향상시켜줍니다.


Backgroud
=========

## Anonymous Inner Class

자바에서는 익명 클래스들이 응용 프로그램에서 오직 한번만 일어나는 클래스를 사용하는 만들 방법을 제공합니다. 예를들어 
이벤트 핸들링 클래스를 각각 만드는 것이 아니라 다음같이 해 줍니다.
```java
     JButton testButton = new JButton("Test Button");
     testButton.addActionListener(new ActionListener(){
     @Override public void actionPerformed(ActionEvent ae){
         System.out.println("Click Detected by Anon Class");
       }
     });
```


