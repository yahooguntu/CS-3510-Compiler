(FUNCTION  fact  [(int x)]
  (BB 2
    (OPER 3 Func_Entry []  [])
    (OPER 5 Mov [(r 3)]  [(i 1)])
    (OPER 6 GT [(r 2)]  [(r 0)(r 3)])
  )
  (BB 3
    (OPER 7 Mul_I [(r 5)]  [(r 0)(r 0)])
    (OPER 8 Mov [(r 4)]  [(r 5)])
    (OPER 9 Mov [(m RetReg)]  [(r 4)])
    (OPER 10 Return []  [(m RetReg)])
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
    (OPER 5 Mov [(r 4)]  [(i 0)])
    (OPER 6 GT [(r 3)]  [(r 2)(r 4)])
  )
  (BB 3
  )
  (BB 4
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
