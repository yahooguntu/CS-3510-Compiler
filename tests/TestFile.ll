(FUNCTION  fact  []
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 Mov [(r 4)]  [(i 1)])
    (OPER 6 GT [(r 3)]  [(r 1)(r 4)])
    (OPER 4 BEQ []  [(r 3)(i 0)(bb 4)])
  )
  (BB 3
    (OPER 7 Mov [(r 8)]  [(i 1)])
    (OPER 8 Sub_I [(r 7)]  [(r 1)(r 8)])
    (OPER 9 Pass []  [(r 7)])
    (OPER 10 JSR []  [(s fact)])
    (OPER 11 Mov [(r 9)]  [(m RetReg)])
    (OPER 12 Mul_I [(r 6)]  [(r 1)(r 9)])
    (OPER 13 Mov [(r 5)]  [(r 6)])
    (OPER 14 Mov [(m RetReg)]  [(r 5)])
    (OPER 15 Return []  [(m RetReg)])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 4
    (OPER 16 Mov [(r 11)]  [(i 1)])
    (OPER 17 Mov [(r 10)]  [(r 11)])
    (OPER 18 Mov [(m RetReg)]  [(r 10)])
    (OPER 19 Return []  [(m RetReg)])
    (OPER 20 Jmp []  [(bb 5)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 4 JSR []  [(s read)])
    (OPER 5 Mov [(r 4)]  [(m RetReg)])
    (OPER 6 Mov [(r 2)]  [(r 4)])
    (OPER 8 Mov [(r 6)]  [(i 0)])
    (OPER 9 GT [(r 5)]  [(r 2)(r 6)])
    (OPER 7 BEQ []  [(r 5)(i 0)(bb 4)])
  )
  (BB 3
    (OPER 10 Pass []  [(r 2)])
    (OPER 11 JSR []  [(s fact)])
    (OPER 12 Mov [(r 7)]  [(m RetReg)])
    (OPER 13 Pass []  [(r 7)])
    (OPER 14 JSR []  [(s write)])
    (OPER 15 Mov [(r 8)]  [(m RetReg)])
  )
  (BB 4
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
