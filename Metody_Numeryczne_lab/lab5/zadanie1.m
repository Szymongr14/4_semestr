function [V, original_Runge, original_sine, interpolated_Runge, interpolated_sine] = zadanie1()
% Rozmiar tablic komórkowych (cell arrays) V, interpolated_Runge, interpolated_sine: [1,4].
% V{i} zawiera macierz Vandermonde wyznaczoną dla liczby węzłów interpolacji równej N(i)
% original_Runge - wektor wierszowy zawierający wartości funkcji Runge dla wektora x_fine=linspace(-1, 1, 1000)
% original_sine - wektor wierszowy zawierający wartości funkcji sinus dla wektora x_fine
% interpolated_Runge{i} stanowi wierszowy wektor wartości funkcji interpolującej 
%       wyznaczonej dla funkcji Runge (wielomian stopnia N(i)-1) w punktach x_fine
% interpolated_sine{i} stanowi wierszowy wektor wartości funkcji interpolującej
%       wyznaczonej dla funkcji sinus (wielomian stopnia N(i)-1) w punktach x_fine
    N = 4:4:16;
    x_fine = linspace(-1, 1, 1000);
    runge_function = @(x) 1 ./ (1 + 25 * x.^2);

    original_Runge = runge_function(x_fine);

    subplot(2,1,1);
    plot(x_fine, original_Runge);
    hold on;
    for i = 1:length(N)
        V{i} = vandermonde_matrix(N(i));% macierz Vandermonde
        x_coarse = linspace(-1, 1, N(i)); % Węzły interpolacji
        y_runge = runge_function(x_coarse); % Wartości funkcji Runge w węzłach interpolacji
        coeff_runge = V{i}\y_runge'; % współczynniki wielomianu interpolującego
        interpolated_Runge{i} = polyval(flipud(coeff_runge), x_fine); % wartosci funkcji interpolującej
        plot (x_fine, interpolated_Runge{i});
    end
    title('Funkcja Rungego');
     xlabel('wartości x');
     ylabel('wartości y');
     legend('oryginalny','4', '8','12','16');
    hold off


    original_sine = sin(2 * pi * x_fine);
    subplot(2,1,2);
    plot(x_fine, original_sine);
    hold on;
    for i = 1:length(N)
        x_coarse = linspace(-1, 1, N(i));
        sin_y = sin(2 * pi * x_coarse);
        coeff_sine = V{i}\sin_y';
        interpolated_sine{i} = polyval(flipud(coeff_sine), x_fine);
        plot (x_fine,interpolated_sine{i});
    end
    title('Funkcja Sinus');
    xlabel('wartości x');
    ylabel('wartości y');
    legend('oryginalny','4', '8','12','16');

    hold off
    print -dpng zadanie1.png 
    hold off
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

