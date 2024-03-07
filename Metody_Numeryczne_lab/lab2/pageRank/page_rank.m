function [numer_indeksu, Edges, I, B, A, b, r] = page_rank()
numer_indeksu = 193141;
d = 0.85;
N = 8;

Edges = [ 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5 ,6, 6, 7, 8;
          6, 4, 4, 5, 3, 8, 5, 6, 7, 6, 5, 4, 6, 7, 4, 6, 5];

I = speye(N);
B = sparse(Edges(2, :), Edges(1, :), 1, N, N);


L = sum(B,1);
A = spdiags(1./L', 0, N,N);
b = (1-d)/N * ones(N, 1);

M = sparse(I - d*B*A);
r = M\b;

end

