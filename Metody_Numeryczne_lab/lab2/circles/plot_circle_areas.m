function plot_circle_areas(circle_areas)
    figure;

    plot(1:size(circle_areas,2), circle_areas, 'b-');
    xlabel('i');
    ylabel('circle areas');
    title('Circle Areas Plot');
    print -dpng zadanie3.png
end
