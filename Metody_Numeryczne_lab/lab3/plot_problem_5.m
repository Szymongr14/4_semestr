function plot_problem_5(N,time_Jacobi,time_Gauss_Seidel,iterations_Jacobi,iterations_Gauss_Seidel)
bar_data = [iterations_Jacobi; iterations_Gauss_Seidel];
% Opis wektorów stanowiących parametry wejściowe:
% N - rozmiary analizowanych macierzy
% time_Jacobi - czasy wyznaczenia rozwiązania metodą Jacobiego
% time_Gauss_Seidel - czasy wyznaczenia rozwiązania metodą Gaussa-Seidla
% iterations_Jacobi - liczba iteracji wymagana do wyznaczenia rozwiązania metodą Jacobiego
% iterations_Gauss_Seide - liczba iteracji wymagana do wyznaczenia rozwiązania metodą Gauss-Seidla
subplot(2,1,1);
plot(N,time_Jacobi); 
hold on; 
plot(N,time_Gauss_Seidel); 
legend('Jacobi','GaussSeidel','Location','eastoutside');
title("Comparison time complexity of Jacobi and GaussSeidel methods");
xlabel('Matrix Size');
ylabel('Computation Time');
hold off;


subplot(2,1,2);
bar(N,bar_data);
title("Comparison number of iterations to get result of Jacobi and GaussSeidel methods");
legend('Jacobi','GaussSeidel','Location','eastoutside');
xlabel('Matrix Size');
ylabel('Number of iterations to get result');
print('zadanie5.png','-dpng')
end