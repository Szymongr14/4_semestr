function [matrix_condition_numbers, max_coefficients_difference_1, max_coefficients_difference_2] = zadanie3()
% Zwracane są trzy wektory wierszowe:
% matrix_condition_numbers - współczynniki uwarunkowania badanych macierzy Vandermonde
% max_coefficients_difference_1 - maksymalna różnica między referencyjnymi a obliczonymi współczynnikami wielomianu,
%       gdy b zawiera wartości funkcji liniowej
% max_coefficients_difference_2 - maksymalna różnica między referencyjnymi a obliczonymi współczynnikami wielomianu,
%       gdy b zawiera zaburzone wartości funkcji liniowej

N = 5:40;

%% chart 1
subplot(3,1,1);
matrix_condition_numbers = zeros(1, length(N));
for i = 1:length(N)
    V = vandermonde_matrix(N(i));
    matrix_condition_numbers(i) = cond(V);
end    
plot(N, matrix_condition_numbers);
set(gca, 'YScale', 'log');
title('współczynnik uwarunkowania macierzy Vandermonde');
xlabel('rozmiar macierzy');
ylabel('wsp uwarunkowania');


%% chart 2
subplot(3,1,2)
a1 = randi([20,30]);
for i = 1:length(N)
    ni = N(i);
    V = vandermonde_matrix(ni);
    
    % Niech wektor b zawiera wartości funkcji liniowej
    b = linspace(0,a1,ni)';
    reference_coefficients = [ 0; a1; zeros(ni-2,1) ]; % tylko a1 jest niezerowy
    
    % Wyznacznie współczynników wielomianu interpolującego
    calculated_coefficients = V \ b;

    max_coefficients_difference_1(i) = max(abs(calculated_coefficients-reference_coefficients));
end
plot(N,  max_coefficients_difference_1);
set(gca, 'YScale', 'log');
title('błąd wyznaczenia wartości współczynników wielomianu ');
xlabel('rozmiar macierzy');
ylabel('błąd wsp');


%% chart 3
subplot(3,1,3)
for i = 1:length(N)
    ni = N(i);
    V = vandermonde_matrix(ni);
    
    % Niech wektor b zawiera wartości funkcji liniowej nieznacznie zaburzone
    b = linspace(0,a1,ni)' + rand(ni,1)*1e-10;
    reference_coefficients = [ 0; a1; zeros(ni-2,1) ]; % tylko a1 jest niezerowy
    
    % Wyznacznie współczynników wielomianu interpolującego
    calculated_coefficients = V \ b;
    
    max_coefficients_difference_2(i) = max(abs(calculated_coefficients-reference_coefficients));
end
plot(N,  max_coefficients_difference_2);
set(gca, 'YScale', 'log');
title('błąd wyznaczenia wartości współczynników wielomianu ');
xlabel('rozmiar macierzy');
ylabel('błąd wsp');
print -dpng zadanie3.png 

end


function V = vandermonde_matrix(N)
    % Generuje macierz Vandermonde dla N równomiernie rozmieszczonych w przedziale [-1, 1] węzłów interpolacji
    x_coarse = linspace(0, 1, N);
    vector = ones(1, N);

    V = [];
    V = [vector',V];
    for i = 1:N-1
       vector = power(x_coarse,i); 
       V = [V,vector'];
    end

end