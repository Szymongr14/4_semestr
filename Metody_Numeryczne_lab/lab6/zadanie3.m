function [country, source, degrees, x_coarse, x_fine, y_original, y_yearly, y_approximation, mse] = zadanie3(energy)
% Głównym celem tej funkcji jest wyznaczenie aproksymacji rocznych danych o produkcji energii elektrycznej w wybranym kraju i z wybranego źródła energii.
% Wybór kraju i źródła energii należy określić poprzez nadanie w tej funkcji wartości zmiennym typu string: country, source.
% Dopuszczalne wartości tych zmiennych można sprawdzić poprzez sprawdzenie zawartości struktury energy zapisanej w pliku energy.mat.
% 
% energy - struktura danych wczytana z pliku energy.mat
% country - [String] nazwa kraju
% source  - [String] źródło energii
% degrees - wektor zawierający cztery stopnie wielomianu dla których wyznaczono aproksymację
% x_coarse - wartości x danych aproksymowanych; wektor o rozmiarze [N,1].
% x_fine - wartości, w których wyznaczone zostaną wartości funkcji aproksymującej; wektor o rozmiarze [P,1].
% y_original - dane wejściowe, czyli pomiary produkcji energii zawarte w wektorze energy.(country).(source).EnergyProduction
% y_yearly - wektor danych rocznych (wektor kolumnowy).
% y_approximation - tablica komórkowa przechowująca cztery wartości funkcji aproksymującej dane roczne.
%   - y_approximation{i} stanowi aproksymację stopnia degrees(i)
%   - y_approximation{i} stanowi wartości funkcji aproksymującej w punktach x_fine.
% mse - wektor o rozmiarze [4,1]: mse(i) zawiera wartość błędu średniokwadratowego obliczonego dla aproksymacji stopnia degrees(i).

country = 'India';
source = 'Coal';
degrees = 1:4;
x_coarse = [];
x_fine = [];
y_original = [];
y_yearly = [];
y_approximation = [];
mse = zeros(4,1);

% Sprawdzenie dostępności danych
if isfield(energy, country) && isfield(energy.(country), source)
    % Przygotowanie danych do aproksymacji
    dates = energy.(country).(source).Dates;
    y_original = energy.(country).(source).EnergyProduction;

    % Obliczenie danych rocznych
    n_years = floor(length(y_original) / 12);
    y_cut = y_original(end-12*n_years+1:end);
    y4sum = reshape(y_cut, [12 n_years]);
    y_yearly = sum(y4sum,1)';

    degrees = 1:4;

    N = length(y_yearly);
    P = 10*N;
    x_coarse = linspace(-1, 1, N)';
    x_fine = linspace(-1, 1, P)';

    % Pętla po wielomianach różnych stopni
    for i = 1:length(degrees)
        p = my_polyfit(x_coarse , y_yearly , degrees(i));
        y_approximation{i} = polyval(p, x_fine);
        y_approximation_coarse =  polyval(p, x_coarse);

        squared_errors = (y_approximation_coarse - y_yearly).^2;
        mse(i) = mean(squared_errors);
    end

else
    disp(['Dane dla (country=', country, ') oraz (source=', source, ') nie są dostępne.']);
end

subplot(2,1,1);
hold on;
plot( x_coarse, y_yearly );
for i = 1:length(degrees)
    plot(x_fine, y_approximation{i});
end

 title('Coal energy production for India');
 xlabel('x');
 ylabel('y');
 legend('original','1 nodes', '2 nodes', '3 nodes', '4 nodes', 'Location', 'best');
 hold off

subplot(2,1,2);
mse = mse';
bar(mse); 
title('Mean square error');  
set(gca, 'XTickLabel', degrees);
xlabel('polynomial degrees'); 
ylabel('MSE value');
print -dpng zadanie3.png

end


function p = my_polyfit(x, y, deg)
    V = ones(length(x), deg+1);
    for i=1:length(x)
        for j=1:deg+1
            if j == deg+1
                V(i,j) = 1;
                continue;
            end
            V(i,j) = power(x(i), deg+1-j);
        end
    end
    
    A = V'*V;
   b = V'*y;
   p = A\b
end

