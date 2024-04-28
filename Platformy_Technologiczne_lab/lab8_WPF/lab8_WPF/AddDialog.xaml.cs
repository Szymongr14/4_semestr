using System.Text.RegularExpressions;
using System.Windows;
using MessageBox = System.Windows.MessageBox;

namespace lab8_WPF;

public partial class InputDialog : Window
{
    public string FileName { get; private set; }
    public bool IsFile { get; private set; }
    public bool IsReadOnly { get; private set; }
    public bool IsArchive { get; private set; }
    public bool IsHidden { get; private set; }
    public bool IsSystem { get; private set; }

    public InputDialog()
    {
        InitializeComponent();
    }

    private void OkButton_Click(object sender, RoutedEventArgs e)
    {
        FileName = FileNameTextBox.Text;
        IsFile = RadioButtonFile.IsChecked == true;
        

        if (IsFile && !Regex.IsMatch(FileName, @"^[a-zA-Z0-9_~-]{1,8}\.(txt|php|html)$"))
        {
            MessageBox.Show("Invalid file name. The file name must consist of a base name (1 to 8 characters: letters, digits, underscore, tilde, minus) and an extension (txt|php|html) separated by a dot.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }

        IsReadOnly = CheckBoxReadOnly.IsChecked == true;
        IsArchive = CheckBoxArchive.IsChecked == true;
        IsHidden = CheckBoxHidden.IsChecked == true;
        IsSystem = CheckBoxSystem.IsChecked == true;
        DialogResult = true;
    }

    private void CancelButton_Click(object sender, RoutedEventArgs e)
    {
        Close();
    }
}