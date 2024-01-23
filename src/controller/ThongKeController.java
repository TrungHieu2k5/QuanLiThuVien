package controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import model.ThongKe;
import view.ViewThuVien;

public class ThongKeController implements MouseListener {
	ViewThuVien thuviens;
	public ThongKeController(ViewThuVien thuviens)  {
		// TODO Auto-generated constructor stub
		this.thuviens = thuviens;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		thuviens.thongKe.getListByBrrow();
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for(ThongKe ke : thuviens.thongKe.list) {
			data.addValue(ke.getSoluongmuon(), "Độc giả", ke.getNgaymuonsach());
		}
		JFreeChart chart = ChartFactory.createAreaChart("Thống kê số lượng mượn sách hàng tháng",
				"Thời gian", "Số lượng", data);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(thuviens.jPanel_thongke.getWidth(), 500));
		thuviens.jPanel_thongke.removeAll();
		thuviens.jPanel_thongke.add(chartPanel);
		thuviens.jPanel_thongke.validate();
		thuviens.jPanel_thongke.repaint();
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
