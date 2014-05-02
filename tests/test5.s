.data
.comm	a,4,4

.text
	.align 4
.globl  addThem
addThem:
addThem_bb2:
	movl	%EDI, %EAX
	movl	%ESI, %EDI
addThem_bb3:
	addl	%EDI, %EAX
addThem_bb1:
	ret
.globl  main
main:
main_bb2:
	pushq	%R15
main_bb3:
	movl	$5, %EAX
	movl	%EAX, %ESI
	movl	$5, %EAX
	cmpl	%EAX, %ESI
	jne	main_bb5
main_bb4:
	movl	$3, %EAX
	movl	%EAX, a(%RIP)
main_bb6:
	movl	$0, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	movl	%EAX, %EDI
	movl	$8, %EAX
	cmpl	%EAX, %EDI
	jg	main_bb8
main_bb7:
	movl	%R15D, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$1, %EAX
	addl	%EAX, %EDI
	movl	$8, %EAX
	cmpl	%EAX, %EDI
	jle	main_bb7
main_bb8:
	movl	$3, %EDI
	movl	$0, %EDX
	movl	%R15D, %EAX
	idivl	%EDI, %EAX
	movl	$4, %EDI
	imull	%EDI, %EAX
	movl	%EAX, %EDI
	movl	%EDI, %R15D
	movl	a(%RIP), %EDI
	call	addThem
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$0, %EAX
main_bb1:
	popq	%R15
	ret
main_bb5:
	movl	$4, %EAX
	movl	%EAX, a(%RIP)
	jmp	main_bb6
