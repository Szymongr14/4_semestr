import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_csv('results1.csv')
threads = df['threads']
computing_time = df['time']
plt.figure()
plt.title("Number of threads in pool vs Computing time")
plt.xlabel('Number of threads in pool')
plt.ylabel('Computing time')
plt.plot(threads, computing_time, label="CPU with 8 physical cores and 16 logical cores")
plt.legend()
plt.show()
