.686
.model flat
.data
	hundred dd 100
	fifty dd 50
	result dd ?
.code
_quantile_function PROC
	push ebp
	mov ebp, esp
	push esi
	push edi
	push ebx

	fld dword PTR [ebp + 8]
	fild dword PTR hundred
	fmul
	fild dword PTR fifty
	fadd

	fst dword PTR result
	mov eax, result
	
	
	pop ebx
	pop edi
	pop esi
	pop ebp
	ret
_quantile_function ENDP

END