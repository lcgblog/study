package com.lcgblog.study.sourcecodehunter.a202310;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.TypeConverter;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResolvableTypeCase {

    public static void main(String[] args) {
        ResolvableType resolvableType = ResolvableType.forClass(User.class);
        System.out.println(resolvableType.getSuperType());
        System.out.println(resolvableType.getInterfaces()[0]);
        System.out.println(resolvableType.resolveGeneric());
        System.out.println(resolvableType.resolve());
        Class<? extends List> targetListClass = UserList.class;
        System.out.println(ResolvableType.forClass(targetListClass).asCollection().resolveGeneric());

    }

    static class Parent{}

    static class User<T extends List<Parent>> extends Parent implements Serializable {
    }

    static class UserList extends ArrayList<Parent>{}
}
