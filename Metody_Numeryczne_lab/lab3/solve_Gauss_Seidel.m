function [A,b,M,bm,x,err_norm,time,iterations,index_number] = solve_Gauss_Seidel(N)

index_number = 193141;
err_norm = 0.00000000001;
iterations = 1000;
L1 = 1;

[A,b] = generate_matrix(N, L1);

U = triu(A, 1);
L = tril(A, -1);
D = diag(diag(A));
M = -(D + L)\U;
bm = (D+L)\b;
x = ones(N,1);

tic;
i = 0;
while true
    x = M*x + bm;
    err = norm(A*x - b);
    if i > iterations || err<err_norm
        break;
    end
    %disp(err);
    i = i + 1;
end
toc;
iterations = i;
time = toc;
end