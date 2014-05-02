(FUNCTION  test  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 4)]  [(i 0)])
    (OPER 5 Mov [(r 2)]  [(r 4)])
    (OPER 6 Mov [(r 5)]  [(i 1)])
    (OPER 7 Mov [(r 3)]  [(r 5)])
    (OPER 8 Mov [(r 6)]  [(i 0)])
    (OPER 9 Mov [(r 3)]  [(r 6)])
    (OPER 10 Mov [(r 2)]  [(r 6)])
    (OPER 12 GT [(r 7)]  [(r 2)(r 3)])
    (OPER 11 BEQ []  [(r 7)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 13 Mov [(r 10)]  [(i 1)])
    (OPER 14 Sub_I [(r 9)]  [(r 3)(r 10)])
    (OPER 15 Mov [(r 3)]  [(r 9)])
  )
  (BB 6
    (OPER 46 EQ [(r 33)]  [(r 2)(r 3)])
    (OPER 45 BEQ []  [(r 33)(i 0)(bb 15)])
  )
  (BB 14
    (OPER 47 EQ [(r 35)]  [(r 2)(r 3)])
    (OPER 48 BEQ []  [(r 35)(i 0)(bb 17)])
  )
  (BB 16
    (OPER 49 EQ [(r 37)]  [(r 2)(r 3)])
    (OPER 50 BEQ []  [(r 37)(i 0)(bb 19)])
  )
  (BB 18
    (OPER 51 Mov [(r 40)]  [(i 1)])
    (OPER 52 Sub_I [(r 39)]  [(r 3)(r 40)])
    (OPER 53 Mov [(r 2)]  [(r 39)])
    (OPER 54 EQ [(r 41)]  [(r 2)(r 3)])
    (OPER 55 BNE []  [(r 41)(i 0)(bb 18)])
  )
  (BB 19
    (OPER 56 EQ [(r 42)]  [(r 2)(r 3)])
    (OPER 57 BNE []  [(r 42)(i 0)(bb 16)])
  )
  (BB 17
  )
  (BB 15
    (OPER 58 Mov [(r 43)]  [(i 3)])
    (OPER 59 Mov [(r 3)]  [(r 43)])
    (OPER 60 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 10
    (OPER 29 Mov [(r 23)]  [(i 1)])
    (OPER 30 Mov [(r 3)]  [(r 23)])
    (OPER 31 Mov [(r 25)]  [(i 1)])
    (OPER 32 EQ [(r 24)]  [(r 3)(r 25)])
    (OPER 33 BEQ []  [(r 24)(i 0)(bb 13)])
  )
  (BB 12
    (OPER 34 Mov [(r 28)]  [(i 2)])
    (OPER 35 Add_I [(r 27)]  [(r 3)(r 28)])
    (OPER 36 Mov [(r 3)]  [(r 27)])
    (OPER 37 Mov [(r 30)]  [(i 1)])
    (OPER 38 EQ [(r 29)]  [(r 3)(r 30)])
    (OPER 39 BNE []  [(r 29)(i 0)(bb 12)])
  )
  (BB 13
    (OPER 40 Jmp []  [(bb 11)])
  )
  (BB 5
    (OPER 16 Mov [(r 12)]  [(i 2)])
    (OPER 17 Mov [(r 3)]  [(r 12)])
    (OPER 18 Mov [(r 14)]  [(i 2)])
    (OPER 19 EQ [(r 13)]  [(r 3)(r 14)])
    (OPER 20 BEQ []  [(r 13)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 21 Mov [(r 16)]  [(i 2)])
    (OPER 22 Mov [(r 3)]  [(r 16)])
    (OPER 24 Mov [(r 18)]  [(i 2)])
    (OPER 25 EQ [(r 17)]  [(r 3)(r 18)])
    (OPER 23 BEQ []  [(r 17)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 26 Mov [(r 21)]  [(i 1)])
    (OPER 27 Add_I [(r 20)]  [(r 3)(r 21)])
    (OPER 28 Mov [(r 3)]  [(r 20)])
  )
  (BB 11
    (OPER 41 Mov [(r 32)]  [(i 2)])
    (OPER 42 EQ [(r 31)]  [(r 3)(r 32)])
    (OPER 43 BNE []  [(r 31)(i 0)(bb 7)])
  )
  (BB 8
    (OPER 44 Jmp []  [(bb 6)])
  )
)
