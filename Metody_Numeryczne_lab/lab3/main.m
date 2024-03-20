N = 100;
[A,b,x,time_direct,err_norm,index_number] = solve_direct(N);
[A,b,M,bm,x1,err_norm,time,iterations,index_number] = solve_Jacobi(N);
[A,b,M,bm,x2,err_norm,time,iterations,index_number] = solve_Gauss_Seidel(N);

N = 1000:1000:8000;
n = length(N);
vtime_direct = ones(1,n); 

for i = 1:n
    [A,b,x,time_direct,err_norm,index_number] = solve_direct(N(i));  
    fprintf("N = %d computing time: %d\n", 1000*i, time_direct);
    vtime_direct(i) = time_direct;
end
plot_direct(N,vtime_direct);



