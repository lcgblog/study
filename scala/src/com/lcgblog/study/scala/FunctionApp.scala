package com.lcgblog.study.scala

object FunctionApp {
  def main(args: Array[String]): Unit = {
    println(sum(1,2,3))
    println(minus(3,1))
    println(minus(b=1,a=3))
    println(minus())
    println(minus(b=5))
    for(i <- 1 to 10){
      println(i)
    }
    for(i <- 1 until 10){
      println(i)
    }
    for(i <- Range(1,10)){
      println(i)
    }
    for(i <- Range.inclusive(1,10)){
      println(i)
    }
    for(i <- Range(1,10) if i % 2 == 0){
      println(i)
    }
    Range(1,10).foreach(i=>{
//      i = i*2//compile error, it is a value not a variable
      println(i)
      println(i*2)
    })
  }

  def sum(values: Int*): Int ={
    var sum = 0;
    for(v<-values){
      sum+=v
    }
    sum
  }

  def minus(a:Int=3, b:Int=1): Int ={
    a - b
  }

}
