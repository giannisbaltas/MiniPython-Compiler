#! /usr/bin/env python
#A miniPython example

import random

def fib(n):    # write Fibonacci series up to n 
    a = 0
    b = 1
    while a < n:
        print a
        a = b
        b = a + b
                      
def funcwithdef(name,university="aueb"):
       print name, " studies in ", university



# start of execution
assert 1 , 1

if 10 < fib(20) and 5 >= 4 :
    print 3+2*3*4

x = None