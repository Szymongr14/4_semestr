function plot_counts_mean(counts_mean)
    plot(1:size(counts_mean,2), counts_mean, 'b-');
    xlabel('i');
    ylabel('mean');
    title('Counts Mean Plot');
    print -dpng zadanie5.png 
end
