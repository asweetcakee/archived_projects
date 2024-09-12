import sqlite3
from kivy.app import App
from kivy.config import Config
from kivy.core.window import Window
from kivy.uix.button import Button
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.widget import Widget
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.scrollview import ScrollView
from kivy.core.window import Window
from kivy.uix.dropdown import DropDown

Config.set('graphics', 'resizable', '0')
Config.write()
Config.set('graphics', 'width', '1000')
Config.write()
Config.set('graphics', 'height', '460')
Config.write()

conn = sqlite3.connect("mangashop/mangashop.db")  
cursor = conn.cursor()

ml = BoxLayout()
gl = GridLayout(rows=2)
rl = BoxLayout(size_hint=(0.2, None), height=50)
tl = BoxLayout(size_hint=(1, None), height=50)
glForrl = GridLayout(rows=2, size_hint=(0.3, 1))
al = BoxLayout(orientation='vertical', size_hint=[1, 1.5])
vl = GridLayout(cols=7,spacing=10, size_hint_y=None)
sl = BoxLayout(orientation='vertical', size_hint=[1, 1.5])

class CourseWork(App):

	def sort(self, object):  # Метод Сортировки													
		if(object.text == "id"): sortValue = "id"
		elif(object.text == "title"): sortValue = "title"
		elif(object.text == "genre"): sortValue = "genre"
		elif(object.text == "author"): sortValue = "author"
		elif(object.text == "publisher"): sortValue = "publisher"
		elif(object.text == "quantity"): sortValue = "quantity"
		elif(object.text == "price($)"): sortValue = "price"

		cursor.execute("SELECT * FROM manga ORDER BY("+ sortValue +")")
		self.scroll.remove_widget(vl)
		vl.clear_widgets()
		rows = cursor.fetchall()
		for row in rows:
			for i in range(len(row)):
				vl.add_widget(Label(text = str(row[i]),size_hint_y=None, height=40, font_size = 12))
		self.scroll.add_widget(vl)

	def choose(self, object):      # метод выбора для DropMenu
		if(object.text == "exit"): self.quit_btn()
		elif(object.text == "search"): self.search()
		elif(object.text == "delete"): self.delExecuted()
		elif(object.text == "update"): self.updateExecuted()
		elif(object.text == "create"): self.createExecuted()

	def search(self):
		sl.clear_widgets(children=None)
		self.searchInp = TextInput(hint_text='Search...')
		self.btnSearch = Button(text = "Search", background_color = [0 , 1 , 1, 1], on_press=self.searchExecuted)
		sl.add_widget(self.searchInp)
		sl.add_widget(self.btnSearch)

	def searchExecuted(self, instance):
		self.scroll.remove_widget(vl)
		vl.clear_widgets()
		cursor.execute("SELECT * FROM `manga`")
		rows = cursor.fetchall()
		#=======================#
		numbArr = []			   #
		for i in range(1000):  #
			i = str(i)			   #
			numbArr.append(i)    #
		#=======================#
		self.searchInp.text = self.searchInp.text.replace(' ', '')
		for row in rows:
			if self.searchInp.text not in numbArr:
				if self.searchInp.text in row:
					for i in range(len(row)):
						vl.add_widget(Label(text = str(row[i]),size_hint_y=None, height=40, font_size = 12))
			elif self.searchInp.text in numbArr:
				ans = int(self.searchInp.text)
				if ans in row:
					for i in range(len(row)):
						vl.add_widget(Label(text = str(row[i]),size_hint_y=None, height=40, font_size = 12))
		self.scroll.add_widget(vl)

	def build(self):

		#================= DROP MENU ==================>
		self.array = (["create"], ["update"], ["delete"], ["search"], ["exit"])		# массив со значениями кнопок
		self.dropdown = DropDown()
		for value in self.array:	# перебираю и даю значения кнопок по элементно(по порядку)
			dropBtn = Button(text=value[0], size_hint_y=None, height=44, background_color = [100, 0, 0, 0.3])
			dropBtn.bind(on_release=self.choose)	# событие для кнопок
			self.dropdown.add_widget(dropBtn)
		self.mainbutton = Button(text='Menu', background_color = [100, 0, 0, 0.5], on_press=self.clearMenu)		# Основная кнопка
		self.mainbutton.bind(on_release=self.dropdown.open)
		self.dropdown.bind(on_select=lambda instance, x: setattr(self.mainbutton, 'text', x))

		#================ Scroll ======================>
		self.scroll = ScrollView(size_hint=(1, None), size=(Window.width, Window.height-70)) 
		vl.bind(minimum_height=vl.setter('height'))

		btnTable1 = Button(text = "id", background_color = [0 , 1 , 1, 1], on_press=self.sort) 	# Все кнопки tl(TopLayout)
		btnTable2 = Button(text = "title", background_color = [0 , 1 , 1, 1], on_press=self.sort) 
		btnTable3 = Button(text = "genre", background_color = [0 , 1 , 1, 1], on_press=self.sort) 
		btnTable4 = Button(text = "author", background_color = [0 , 1 , 1, 1], on_press=self.sort) 
		btnTable5 = Button(text = "publisher", background_color = [0 , 1 , 1, 1], on_press=self.sort) 
		btnTable6 = Button(text = "quantity", background_color = [0 , 1 , 1, 1], on_press=self.sort) 
		btnTable7 = Button(text = "price($)", background_color = [0 , 1 , 1, 1], on_press=self.sort) 

		ml.add_widget(gl)
		gl.add_widget(tl)
		gl.add_widget(al)
		ml.add_widget(glForrl)
		glForrl.add_widget(rl)
		glForrl.add_widget(sl)
		al.add_widget(self.scroll)
		self.Show()

		tl.add_widget(btnTable1)
		tl.add_widget(btnTable2)
		tl.add_widget(btnTable3)
		tl.add_widget(btnTable4)
		tl.add_widget(btnTable5)
		tl.add_widget(btnTable6)
		tl.add_widget(btnTable7)
		rl.add_widget(self.mainbutton)

		return ml

	def Show(self):
		self.scroll.remove_widget(vl)
		vl.clear_widgets()
		cursor.execute("SELECT * FROM `manga`")
		rows = cursor.fetchall()
		for row in rows:
			for i in range(len(row)):
				vl.add_widget(Label(text = str(row[i]),size_hint_y=None, height=40, font_size = 12))
		self.scroll.add_widget(vl)

	def clearShow(self, instance): 				# Очистка экрана и вывод таблицы
		al.clear_widgets(children=None)
		al.add_widget(self.scroll)
		self.Show()

	def clearMenu(self, instance):
		sl.clear_widgets(children=None)

	def quit_btn(self):
		Window.close()	
	
	def delExecuted(self):
		sl.clear_widgets(children=None)
		self.delInp = TextInput(hint_text='Enter id...')
		accessBtn = Button(text = "enter", on_press=self.delAns)
		sl.add_widget(self.delInp)		
		sl.add_widget(accessBtn)
		
	def delAns(self, instance):
		sl.clear_widgets(children=None)
		delRows = "DELETE FROM `manga` WHERE id IN (?)"	
		cursor.execute(delRows, (self.delInp.text,))
		conn.commit()
		self.clearShow(1)	

	def updateExecuted(self):
		sl.clear_widgets(children=None)
		self.idInp = TextInput(hint_text='ID...')
		self.titleInp = TextInput(hint_text='Title...')
		self.genreInp = TextInput(hint_text='Genre...')
		self.authorInp = TextInput(hint_text='Author...')
		self.publisherInp= TextInput(hint_text='Publisher...')
		self.quantityInp = TextInput(hint_text='Quantity...')
		self.priceInp = TextInput(hint_text='Price...')
		accessBtn = Button(text = "enter", on_press=self.updateAns)
		sl.add_widget(self.idInp)		
		sl.add_widget(self.titleInp)		
		sl.add_widget(self.genreInp)		
		sl.add_widget(self.authorInp)		
		sl.add_widget(self.publisherInp)		
		sl.add_widget(self.quantityInp)		
		sl.add_widget(self.priceInp)		
		sl.add_widget(accessBtn)

	def updateAns(self, instance):
		sl.clear_widgets(children=None)
		self.idInp = self.idInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.titleInp = self.titleInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.genreInp = self.genreInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.authorInp = self.authorInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.publisherInp = self.publisherInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.quantityInp = self.quantityInp.text.replace(' ', '').replace('\t','').replace('\t','')
		self.priceInp = self.priceInp.text.replace(' ', '').replace('\t','').replace('\t','')
		updateQuery = """
                    UPDATE manga
                    SET title = ?, genre = ?, author = ?, publisher = ?, quantity = ?, price = ?
                    WHERE id = ?
                """
		cursor.execute(updateQuery, (
                    self.titleInp.text,
                    self.genreInp.text,
                    self.authorInp.text,
                    self.publisherInp.text,
                    self.quantityInp.text,
                    self.priceInp.text,
                    self.idInp.text
                ))
		conn.commit()
		self.clearShow(1)

	def createExecuted(self):
		sl.clear_widgets(children=None)
		self.titleInp = TextInput(hint_text='Title...')
		self.genreInp = TextInput(hint_text='Genre...')
		self.authorInp = TextInput(hint_text='Author...')
		self.publisherInp= TextInput(hint_text='Publisher...')
		self.quantityInp = TextInput(hint_text='Quantity...')
		self.priceInp = TextInput(hint_text='Price...')
		accessBtn = Button(text = "enter", on_press=self.createAns)
		sl.add_widget(self.titleInp)		
		sl.add_widget(self.genreInp)		
		sl.add_widget(self.authorInp)		
		sl.add_widget(self.publisherInp)		
		sl.add_widget(self.quantityInp)		
		sl.add_widget(self.priceInp)		
		sl.add_widget(accessBtn)

	def createAns(self, instance):
		sl.clear_widgets(children=None)
		self.titleInp = self.titleInp.text.replace(' ', '').replace('\t','')
		self.genreInp = self.genreInp.text.replace(' ', '').replace('\t','')
		self.authorInp = self.authorInp.text.replace(' ', '').replace('\t','')
		self.publisherInp = self.publisherInp.text.replace(' ', '').replace('\t','')
		self.quantityInp = self.quantityInp.text.replace(' ', '').replace('\t','')
		self.priceInp = self.priceInp.text.replace(' ', '').replace('\t','')
		insertQuery = """
                    INSERT INTO manga (title, genre, author, publisher, quantity, price)
                    VALUES (?, ?, ?, ?, ?, ?)
                """
		cursor.execute(insertQuery, (
                    self.titleInp.text,
                    self.genreInp.text,
                    self.authorInp.text,
                    self.publisherInp.text,
                    self.quantityInp.text,
                    self.priceInp.text
                ))
		conn.commit()
		self.clearShow(1)

CourseWork().run()
