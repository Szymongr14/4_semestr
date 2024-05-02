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
    original_Runge = @(x_fine) 1./(1 + 25*x_fine.^2);

    subplot(2,1,1);
    % plot(x_fine, original_Runge);
    hold on;
    for i = 1:length(N)
        V{i} = vandermonde_matrix(N(i));% macierz Vandermonde
        x_coarse = linspace(-1, 1, N(i)); % Węzły interpolacji
        y_runge = original_Runge(x_coarse); % Wartości funkcji Runge w węzłach interpolacji
        coeff_runge = V{i}\y_runge; % współczynniki wielomianu interpolującego
        interpolated_Runge{i} = polyval(flipud(coeff_runge), x_fine); % wartosci funkcji interpolującej
        % plot interpolated_Runge{i}
    end
    hold off

    original_sine = sin(2 * pi * x_fine);
    subplot(2,1,2);
    plot(x_fine, original_sine);
    hold on;
    for i = 1:length(N)
        interpolated_sine{i} = [];
        % plot interpolated_sine{i}
    end
    hold off
end

function V = vandermonde_matrix(N)
    x_coarse = linspace(-1, 1, N);
    V = zeros(N, N); % Inicjalizacja macierzy V o rozmiarze NxN
    for i = 1:N
        for j = 1:N
            V(i,j) = x_coarse(i) ^ (j-1);
        end
    end
end