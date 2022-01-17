package com.alphaa.inzodiac.app.utils;

import java.util.ArrayList;
import java.util.List;

public class StackSet<T> {
    private List<T> stack = new ArrayList<>();

    public int getSize(){
        return stack.size();
    }

    // Push element into stack
    public void push(T e) {
        int index = stack.indexOf(e);
        if (index != -1) {
            stack.remove(index);
        }
        stack.add(e);
    }

    // Pop element from stack
    public T pop() {
        if (getSize() > 0) {
            return stack.remove(getSize()-1);
        } else {
            return null;
        }
    }

    // Look at upper element of stack, don't pop it
    public T peek() {
        if (getSize() > 0) {
            return stack.get(getSize()-1);
        } else {
            return null;
        }
    }

}
