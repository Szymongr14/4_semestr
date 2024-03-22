load("filtr_dielektryczny.mat")

[A,b,x,time_direct,err_norm_direct] = direct(A, b);
fprintf("[DIRECT] computing time: %d\n",time_direct);

[A,b,M,bm,x,err_norm_Jacobi,time_Jacobi,iterations_Jacobi] = Jacobi(A, b);
fprintf("[JACOBI] computing time: %d\n",time_direct);

[A,b,M,bm,x,err_norm_GaussSeidel,time_GaussSeidel,iterations_GaussSeidel] = Gauss_Seidel(A, b);
fprintf("[GAUSS_SEIDEL] computing time: %d\n",time_direct); 


