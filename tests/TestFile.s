.text
	.align 4
.globl  fact
fact:
fact_bb2:
	pushq	%R15
	movl	%EDI, %R15D
fact_bb3:
	movl	$1, %EAX
	cmpl	%EAX, %R15D
	jle	fact_bb5
fact_bb4:
	movl	$48, %EDI
	addl	%R15D, %EDI
	call	putchar
	movl	$1, %EDI
	movl	%R15D, %ESI
	subl	%EDI, %ESI
	movl	%ESI, %EDI
	call	fact
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	imull	%EDI, %EAX
	movl	%EAX, %R15D
	movl	$48, %EAX
	addl	%R15D, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	%R15D, %EAX
fact_bb1:
	popq	%R15
	ret
fact_bb5:
	movl	$49, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$1, %EAX
	jmp	fact_bb1
.globl  main
main:
main_bb2:
main_bb3:
	movl	$3, %EDI
	call	fact
	movl	%EAX, %EDI
	movl	$48, %EAX
	addl	%EDI, %EAX
	movl	%EAX, %EDI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %EDI
	call	putchar
main_bb1:
	ret
