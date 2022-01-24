# errors that i remember from his tests:
# [...]
# a.func() FIXED
# "expected ,"  error not shown
# "expected ]"  error not shown 

#this seems correct
#b = [1+3, 2*a, func()]

#this seems correct
#a = func(1,2)

#this seems correct
#a = func(1,2,)

# these seem correct
# a = [1, 
# a = [1

#also, can't we put this as an expression rule: Expression ( "+" | "-" | "*" | "/" | "%" | "**" ) Expression
# so that we can something like func(1,func() + 1) ?

#this doesn't work cause we can't have expression + operator + expression:
# a = 1 + func()

#this is fixed
a = a.func()