%N = 100;
%[A,b,x,time_direct,err_norm,index_number] = solve_direct(N);
[A,b,x1,time_direct,err_norm,index_number] = solve_Jacobi(N);
%[A,b,x2,time_direct,err_norm,index_number] = solve_Gauss_Seidel(N);

N = 1000:1000:8000;
n = length(N);
vtime_direct = ones(1,n); 

for i = 1:n
    [A,b,x,time_direct,err_norm,index_number] = solve_direct(N(i));  
    disp(time_direct);
    vtime_direct(i) = time_direct;
end
plot_direct(N,vtime_direct);



