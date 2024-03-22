function [A,b,M,bm,x,err_norm,time,iterations] = Gauss_Seidel(A, b)

err_norm = 1E-11;
iterations = 1000;


U = triu(A, 1);
L = tril(A, -1);
D = diag(diag(A));
M = -(D + L)\U;
bm = (D+L)\b;
x = ones(length(A),1);

tic;
i = 0;
while true
    x = M*x + bm;
    err = norm(A*x - b);
    if i > iterations || err<err_norm
        break;
    end
    disp(err);
    i = i + 1;
end
toc;
iterations = i;
err_norm = err;
time = toc;
end