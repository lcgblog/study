package com.lcgblog.study.scala

object ClassApp {
  def main(args: Array[String]): Unit = {
    var person = new Parent()
  }
}

class Parent{
  private[this] var name:String = _ //相当于private var
  private[scala] var name2:String = _ //只有scala包下才能访问
  protected var name3:String = _
  private[lcgblog] var name4:String = _ //只有lcgblog包下才能访问

  val TYPE_NAME = "Person"
}
