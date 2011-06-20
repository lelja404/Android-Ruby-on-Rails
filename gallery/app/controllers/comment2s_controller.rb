class Comment2sController < ApplicationController
  # GET /comment2s
  # GET /comment2s.xml
  def index
    @comment2s = Comment2.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @comment2s }
    end
  end

  # GET /comment2s/1
  # GET /comment2s/1.xml
  def show
    @comment2 = Comment2.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @comment2 }
    end
  end

  # GET /comment2s/new
  # GET /comment2s/new.xml
  def new
    @comment2 = Comment2.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @comment2 }
    end
  end

  # GET /comment2s/1/edit
  def edit
    @comment2 = Comment2.find(params[:id])
  end

  # POST /comment2s
  # POST /comment2s.xml
  def create
    @comment2 = Comment2.new(params[:comment2])

    respond_to do |format|
      if @comment2.save
        format.html { redirect_to(@comment2, :notice => 'Comment2 was successfully created.') }
        format.xml  { render :xml => @comment2, :status => :created, :location => @comment2 }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @comment2.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /comment2s/1
  # PUT /comment2s/1.xml
  def update
    @comment2 = Comment2.find(params[:id])

    respond_to do |format|
      if @comment2.update_attributes(params[:comment2])
        format.html { redirect_to(@comment2, :notice => 'Comment2 was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @comment2.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /comment2s/1
  # DELETE /comment2s/1.xml
  def destroy
    @comment2 = Comment2.find(params[:id])
    @comment2.destroy

    respond_to do |format|
      format.html { redirect_to(comment2s_url) }
      format.xml  { head :ok }
    end
  end
end
