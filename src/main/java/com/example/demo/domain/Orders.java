package com.example.demo.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity = SysUser.class)
	private SysUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	private String bookName;
	private Long bookId;
	private Integer count;
	private Integer totalPrice;
	@Temporal(TemporalType.DATE)
	private Date time;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getTime() {
		return time;
	}

	public Orders(String bookName,  Long bookId, Integer count, Integer totalPrice, Date time) {
		this.bookName = bookName;
		this.bookId = bookId;
		this.count = count;
		this.totalPrice = totalPrice;
		this.time = time;
	}

	public Orders() {

	}

	public void setTime(Date time) {
		this.time = time;
	}
}
