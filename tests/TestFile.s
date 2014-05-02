.text
	.align 4
.globl  fact
fact:
fact_bb2:
	pushq	%R15
fact_bb3:
	movl	$1, %EAX
	cmpl	%EAX, %R15D
	jle	fact_bb5
fact_bb4:
	movl	$1, %ESI
	movl	%R15D, %EDX
	subl	%ESI, %EDX
	movl	%EDX, %ESI
	call	fact
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	imull	%EDI, %EAX
fact_bb1:
	popq	%R15
	ret
fact_bb5:
	movl	$1, %EAX
	jmp	fact_bb1
.globl  main
main:
main_bb2:
main_bb3:
	movl	$3, %ESI
	call	fact
	movl	%EAX, %ESI
	movl	$48, %EAX
	addl	%ESI, %EAX
	movl	%EAX, %ESI
	call	putchar
	movl	$10, %EAX
	movl	%EAX, %ESI
	call	putchar
main_bb1:
	ret
