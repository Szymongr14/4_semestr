function [A,b,x,time_direct,err_norm] = direct(A, b)

tic;
x = ones(length(A), 1);
x = A\b;
time_direct = toc;
err_norm = norm(A*x-b);
end