(FUNCTION  fact  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 5 Mov [(r 4)]  [(i 1)])
    (OPER 6 GT [(r 3)]  [(r 1)(r 4)])
    (OPER 4 BEQ []  [(r 3)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 7 Mov [(r 7)]  [(i 1)])
    (OPER 8 Sub_I [(r 6)]  [(r 1)(r 7)])
    (OPER 9 Pass []  [(r 6)])
    (OPER 10 JSR []  [(s fact)])
    (OPER 11 Mov [(r 8)]  [(m RetReg)])
    (OPER 12 Mul_I [(r 5)]  [(r 1)(r 8)])
    (OPER 13 Mov [(m RetReg)]  [(r 5)])
  )
  (BB 6
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 5
    (OPER 14 Mov [(r 9)]  [(i 1)])
    (OPER 15 Mov [(m RetReg)]  [(r 9)])
    (OPER 16 Jmp []  [(bb 6)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 4)]  [(i 3)])
    (OPER 5 Mov [(r 2)]  [(r 4)])
    (OPER 6 Pass []  [(r 2)])
    (OPER 7 JSR []  [(s fact)])
    (OPER 8 Mov [(r 6)]  [(m RetReg)])
    (OPER 9 Mov [(r 2)]  [(r 6)])
    (OPER 10 Mov [(r 8)]  [(i 48)])
    (OPER 11 Add_I [(r 7)]  [(r 8)(r 2)])
    (OPER 12 Pass []  [(r 7)])
    (OPER 13 JSR []  [(s putchar)])
    (OPER 14 Mov [(r 9)]  [(m RetReg)])
    (OPER 15 Mov [(r 10)]  [(i 10)])
    (OPER 16 Pass []  [(r 10)])
    (OPER 17 JSR []  [(s putchar)])
    (OPER 18 Mov [(r 11)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
