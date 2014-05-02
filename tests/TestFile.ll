(FUNCTION  fact  [(int x)]
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 Mov [(r 3)]  [(i 1)])
    (OPER 6 GT [(r 2)]  [(r 0)(r 3)])
    (OPER 4 BEQ []  [(r 2)(i 0)(bb 4)])
  )
  (BB 3
    (OPER 7 Mov [(r 7)]  [(i 1)])
    (OPER 8 Sub_I [(r 6)]  [(r 0)(r 7)])
    (OPER 9 Pass []  [(r 6)])
    (OPER 10 JSR []  [(s fact)])
    (OPER 11 Mov [(r 8)]  [(m RetReg)])
    (OPER 12 Mul_I [(r 5)]  [(r 0)(r 8)])
    (OPER 13 Mov [(r 4)]  [(r 5)])
    (OPER 14 Mov [(m RetReg)]  [(r 4)])
    (OPER 15 Return []  [(m RetReg)])
  )
  (BB 5
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
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
