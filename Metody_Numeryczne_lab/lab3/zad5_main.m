N = 1000:1000:8000;
n = length(N);
time_Jacobi = ones(1,n);
time_Gauss_Seidel = ones(1,n);
iterations_Jacobi = ones(1,n);
iterations_Gauss_Seidel = ones(1,n);

for i= 1:n
    [A,b,M,bm,x1,err_norm,time,iterations,index_number] = solve_Jacobi(N(i));
    iterations_Jacobi(i) = iterations;
    time_Jacobi(i) = time;
    fprintf("[JACOBI] N = %d computing time: %d\n", 1000*i, time);

    [A,b,M,bm,x1,err_norm,time,iterations,index_number] = solve_Gauss_Seidel(N(i));
    iterations_Gauss_Seidel(i) = iterations;
    time_Gauss_Seidel(i) = time;
    fprintf("[Gauss_Seidel] N = %d computing time: %d\n", 1000*i, time);
end
plot_problem_5(N,time_Jacobi,time_Gauss_Seidel,iterations_Jacobi,iterations_Gauss_Seidel);