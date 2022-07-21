package com.app.utils.printers;

import com.app.persistence.entities.Product;
import com.app.persistence.repositories.ReceiptRepository;
import com.app.persistence.repositories.StockRepository;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;

import static com.app.utils.enums.ProductType.Food;

public class StockPrinterIO {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ReceiptRepository receiptRepository;

    public void printTXTStockRecordLocation(String city){
        var stock = stockRepository.findByAddress_City(city);
        var product = stock.getProduct();
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\Acer\\Desktop\\faks\\III GODINA\\JAVA\\Stock Reports" + "stock_items" + ".txt");
            fileWriter.write(String.format("%10s|", stock.getAddress()));
            fileWriter.write(String.format("%10s|", product.getName()));
            fileWriter.write(String.format("%10s|", product.getProductCode()));
            fileWriter.write(String.format("%10s|", product.getDiscount()));
            fileWriter.write(String.format("%10s|", product.getPrice()));
            fileWriter.write(String.format("%10s|", product.getType()));
            if(product.getType().equals(Food))
                fileWriter.write(String.format("%5s", stock.getQuantityPerProduct()));
            else
                fileWriter.write(String.format("%5s%2s", stock.getQuantityPerProduct(), "kg"));

            System.lineSeparator();
            fileWriter.write("-----------------------------------------");
            System.lineSeparator();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printTXTAllStockRecords(){
        var stocks = stockRepository.findAll();

        stocks.forEach(stock ->{printTXTStockRecordLocation(stock.getAddress().getCity());});
    }

    public void printStockRecordPDF(String city){
        var stock = stockRepository.findByAddress_City(city);

        //PdfDocument pdfDocument = new PdfDocument(new PdfWriter("C:\\Users\\Acer\\Desktop\\faks\\III GODINA\\JAVA\\Stock Reports"));
    }
}
