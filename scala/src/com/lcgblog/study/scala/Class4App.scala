package com.lcgblog.study.scala

object Class4App {
  def main(args: Array[String]): Unit = {
    var child = new Child2("张三",18,"男");
    println(child.name)
    println(child.gender)
    child.eat();
    println(child.maxAge)
    println(child.minAge)
    var parent2 = new Parent2("111",188) {
      override def minAge = 4L
    }
    parent2.eat()
    println(parent2.minAge)
  }
}

abstract class Parent2(var name:String, var age:Int){
  println("Parent enter")

  def eat(): Unit ={
    println("Parent eat")
  }

  def maxAge = 0L

  def minAge:Long

  println("Parent leave")
}

class Child2(name:String, age:Int, var gender:String) extends Parent2(name,age){
  println("Child enter")

  override def eat(): Unit ={
    println("Child eat")
  }

  override def maxAge = 1L

  println("Child leave")

  override def minAge = 2L
}