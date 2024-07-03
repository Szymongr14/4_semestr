function [integration_error, Nt, ft_5, integral_1000] = zadanie2()
    % Numeryczne całkowanie metodą trapezów.
    % Nt - wektor zawierający liczby podprzedziałów całkowania
    % integration_error - integration_error(1,i) zawiera błąd całkowania wyznaczony
    %   dla liczby podprzedziałów równej Nt(i). Zakładając, że obliczona wartość całki
    %   dla Nt(i) liczby podprzedziałów całkowania wyniosła integration_result,
    %   to integration_error(1,i) = abs(integration_result - reference_value),
    %   gdzie reference_value jest wartością referencyjną całki.
    % ft_5 - gęstość funkcji prawdopodobieństwa dla n=5
    % integral_1000 - całka od 0 do 5 funkcji gęstości prawdopodobieństwa
    %   dla 1000 podprzedziałów całkowania

    reference_value = 0.0473612919396179; % wartość referencyjna całki

    Nt = 5:50:10^4; % tablica zawierająca podprzedziały
    integration_error = zeros(1, length(Nt));
    for i = 1:length(Nt)
        N = Nt(i);
        integration_result = trapezoid_method(@f, N);
        integration_error(i) = abs(integration_result - reference_value);
    end
    ft_5 = f(5); % wartość funkcji w x =5 
    integral_1000 = trapezoid_method(@f, 1000); % wartość referencyjna sprawdzająca poprawność funkcji całkującej

    figure();

    loglog(Nt,integration_error);
    title("Wykres jakości całkowania do liczby podprzedziałów");
    xlabel("Liczba przedzialow ");
    ylabel("Blad calkowania");

end

function integral = trapezoid_method(function_, N)
    a = 0;
    b = 5;
    
    delta_x = (b - a) / N;
    
    integral = 0;
    for i = 1:N
        xi = a + (i - 1) * delta_x;
        xip1 = a + i * delta_x;
        
        integral = integral + 0.5 * (function_(xi) + function_(xip1)) * delta_x;
    end
end

function y = f(t)
    sigma = 3;
    
    mi = 10;
    y = 1 / (sigma * sqrt(2 * pi)) * exp(-(t-mi)^2 / (2 * sigma^2));
end