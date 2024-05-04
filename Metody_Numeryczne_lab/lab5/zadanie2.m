function [nodes_Chebyshev, V, V2, original_Runge, interpolated_Runge, interpolated_Runge_Chebyshev] = zadanie2()
% nodes_Chebyshev - wektor wierszowy zawierający N=16 węzłów Czebyszewa drugiego rodzaju
% V - macierz Vandermonde obliczona dla 16 węzłów interpolacji rozmieszczonych równomiernie w przedziale [-1,1]
% V2 - macierz Vandermonde obliczona dla węzłów interpolacji zdefiniowanych w wektorze nodes_Chebyshev
% original_Runge - wektor wierszowy zawierający wartości funkcji Runge dla wektora x_fine=linspace(-1, 1, 1000)
% interpolated_Runge - wektor wierszowy wartości funkcji interpolującej określonej dla równomiernie rozmieszczonych węzłów interpolacji
% interpolated_Runge_Chebyshev - wektor wierszowy wartości funkcji interpolującej wyznaczonej
%       przy zastosowaniu 16 węzłów Czebyszewa zawartych w nodes_Chebyshev 
    N = 16;
    x_fine = linspace(-1, 1, 1000);
    nodes_Chebyshev = get_Chebyshev_nodes(N);

    runge_function = @(x) 1 ./ (1 + 25 * x.^2);
    original_Runge = runge_function(x_fine);

    % Rownomierne rozmieszczenie -----------
    subplot(2,1,1);
    plot(x_fine, original_Runge);
    hold on;

    V = vandermonde_matrix(N);% macierz Vandermonde;
    x_coarse = linspace(-1,1, N);
    y_runge = runge_function(x_coarse);
    coeff_runge = V\y_runge';
    interpolated_Runge = polyval(flipud(coeff_runge), x_fine);

    plot (x_fine,interpolated_Runge);
    plot(x_coarse, y_runge, 'o');
    title('Interpolacja z wykorzystaniem węzłow rozmieszczonym równomiernie');
    xlabel('x');
    ylabel('y');
    legend('oryginalny','interpolacja','wartości w węzłach');
    hold off

    
% Węzły Chebyshewa --------------------------------
    subplot(2,1,2);
    plot(x_fine, original_Runge);
    hold on;
    
    V2 = vandermonde_matrix_Chebyshev(N,nodes_Chebyshev);% macierz Vandermonde
    y_cheb = runge_function(nodes_Chebyshev);% wartości funkcji interpolowanej w węzłach interpolacji
    coeff_cheb = V2\y_cheb'; % współczynniki wielomianu interpolującego
    interpolated_Runge_Chebyshev  = polyval(flipud(coeff_cheb), x_fine); % interpolacja
       
    plot (x_fine,interpolated_Runge_Chebyshev );
    plot(nodes_Chebyshev, y_cheb, 'o');
    title('Interpolacja z wykorzystaniem węzłów Chebyshewa');
    xlabel('x'); % Etykieta dla osi x
    ylabel('y'); % Etykieta dla osi y
    legend('oryginalny','interpolacja ','wartości węzłach');

    hold off
    print -dpng zadanie2.png 
    
end

function nodes = get_Chebyshev_nodes(N)
    % oblicza N węzłów Czebyszewa drugiego rodzaju
    nodes = ones(1,N);
    for i = 1:N
        k = i-1;
        x = (k*pi)/(N-1);
        nodes(i) = cos(x);
    end    
end

function V = vandermonde_matrix_Chebyshev(N,x_coarse)
    % Generuje macierz Vandermonde dla N równomiernie rozmieszczonych w przedziale [-1, 1] węzłów interpolacji
    
    vector = ones(1, N);

    V = [];
    V = [vector',V];
    for i = 1:N-1
       vector = power(x_coarse,i); 
       V = [V,vector'];
    end

end

function V = vandermonde_matrix(N)
    % Generuje macierz Vandermonde dla N równomiernie rozmieszczonych w przedziale [-1, 1] węzłów interpolacji
    x_coarse = linspace(-1,1,N);
    vector = ones(1, N);

    V = [];
    V = [vector',V];
    for i = 1:N-1
       vector = power(x_coarse,i); 
       V = [V,vector'];
    end

end