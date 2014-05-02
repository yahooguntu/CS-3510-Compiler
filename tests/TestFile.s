.text
	.align 4
.globl  fact
fact:
fact_bb2:
	pushq	%R15
	movl	$1, %EAX
	cmpl	%EAX, %R15D
	jle	fact_bb4
fact_bb3:
	movl	$1, %ESI
	movl	%R15D, %EDX
	subl	%ESI, %EDX
	movl	%EDX, %ESI
	call	fact
	movl	%EAX, %EDI
	movl	%R15D, %EAX
	imull	%EDI, %EAX
	popq	%R15
	ret
fact_bb1:
	popq	%R15
	ret
fact_bb4:
	movl	$1, %EAX
	ret
	jmp	fact_bb1
.globl  main
main:
main_bb2:
	call	read
	movl	%EAX, %ESI
	movl	$0, %EAX
	cmpl	%EAX, %ESI
	jle	main_bb1
main_bb3:
	call	fact
	movl	%EAX, %ESI
	call	write
main_bb1:
	ret
