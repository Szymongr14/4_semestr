function plot_circles(a, circles, index_number)
    % Ustawienie osi
    axis equal;
    axis([0 a 0 a]);
    hold on;
    
    % Rysowanie okręgów
    L1 = mod(index_number, 10); % Przykładowo, załóżmy, że index_number jest znane
    if mod(L1, 2) == 0 % Jeśli L1 jest liczbą parzystą
        for i = 1:size(circles, 1)
            plot_circle(circles(i, 3), circles(i, 1), circles(i, 2));
            pause(0.1);
        end
    else % Jeśli L1 jest liczbą nieparzystą
        for i = 1:size(circles, 2)
            plot_circle(circles(1, i), circles(2, i), circles(3, i));
            pause(0.1);
        end
    end
    
    hold off;
end

