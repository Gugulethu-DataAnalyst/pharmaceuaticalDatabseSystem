package dao; 
import dao.salesDao; 
import models.sales; 
import models.salesItem; 
import java.time.LocalDateTime; 
import java.util.ArrayList; 
import java.util.List;

public class saleTestDao { 
        public static void main(String[] args) { 
                /*===================================================== 
                CREATING SALES DAO 
                =====================================================*/ 
                salesDao salesDAO = new salesDao(); 
                
                /*===================================================== 
                CREATING SALE 
                =====================================================*/ 
                sales sale = new sales( 
                        0, 
                        LocalDateTime.now(), 
                        500.00, 
                        1 
                ); 
                /*===================================================== 
                CREATING SALE ITEMS '
                '=====================================================*/ 
                List<salesItem> saleItems = new ArrayList<>(); 
                salesItem item = new salesItem( 
                        0, 
                        0,
                        1, 
                        2, 
                        250.00 
                ); 
                
                saleItems.add(item); 
                
                /*===================================================== 
                PROCESSING SALE 
                =====================================================*/ 
                System.out.println( "========================================" ); 
                System.out.println( "TESTING SALES DAO" ); 
                System.out.println( "========================================" ); 
                boolean result = salesDAO.processSale( sale, saleItems ); 
                /*===================================================== 
                CHECK RESULT
                 =====================================================*/ 
                 if (result) { 
                        System.out.println( "TEST PASSED!" ); 
                        System.out.println( "Sale was processed successfully." ); 
                } else { 
                        System.out.println( "TEST FAILED!" ); 
                        System.out.println( "Sale could not be processed." ); 
                } 
                
                System.out.println( "========================================" ); 
        } 
}