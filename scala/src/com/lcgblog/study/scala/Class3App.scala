package com.lcgblog.study.scala

object Class3App {
  def main(args: Array[String]): Unit = {
    var person = new Person();
//    person.name//unavailable
    println(person.age)
    println(person.id)
    println(person.gender)
  }
}

class Person(name: String, var age: Int, val id:Long) {//主构造器

  var gender:String = _

  //附属构造器
  def this() = this("张三", 18, 1L)

  def this(name: String, age: Int, gender: String){
    this(name,age,1L)//第一行必须是调用主构造器或者附属构造器
    this.gender = gender
//    this.name = "123"//unchangeable
  }
}