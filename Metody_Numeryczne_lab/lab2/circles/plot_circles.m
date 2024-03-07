function plot_circles(a, circles, index_number)
    
    figure;
    axis equal;
    axis([-0.1*a 1.1*a -0.1*a 1.1*a]);
    hold on;
    
    rectangle('Position', [0, 0, a, a], 'EdgeColor', 'b', 'LineWidth', 1);



    for i = 1:size(circles, 2)
        plot_circle(circles(1, i), circles(2, i), circles(3, i));
        pause(0.1);
    end

    
    hold off;
end